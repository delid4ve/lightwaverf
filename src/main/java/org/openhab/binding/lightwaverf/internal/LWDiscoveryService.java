/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.lightwaverf.internal;

import static org.openhab.binding.lightwaverf.internal.LWBindingConstants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerService;
import org.openhab.binding.lightwaverf.internal.smart.dto.api.*;
import org.openhab.binding.lightwaverf.internal.smart.handler.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link lightwaverfBindingConstants} class defines common constants, which
 * are used across the whole binding.
 *
 * @author David Murton - Initial contribution
 */

@Component(service = LWDiscoveryService.class, immediate = true, configurationPid = "discovery.lightwaverf")

public class LWDiscoveryService extends AbstractDiscoveryService implements ThingHandlerService, DiscoveryService {
    private final Logger logger = LoggerFactory.getLogger(LWDiscoveryService.class);
    private static final int DISCOVER_TIMEOUT_SECONDS = 10;
    private LWAccountHandler accountHandler;
    private ScheduledFuture<?> scanTask;
    private ThingTypeUID thingTypeUid;

    public LWDiscoveryService() {
        super(LWBindingConstants.DISCOVERABLE_THING_TYPE_UIDS, DISCOVER_TIMEOUT_SECONDS, true);
    }

    @Override
    protected void activate(Map<String, Object> configProperties) {
        logger.debug("Activate Background Discovery");
        super.activate(configProperties);
    }

    @Override
    public void deactivate() {
        logger.debug("Deactivate Background discovery");
        super.deactivate();
    }

    @Override
    @Modified
    protected void modified(Map<String, Object> configProperties) {
        super.modified(configProperties);
    }

    @Override
    public void startBackgroundDiscovery() {
        logger.debug("Start Background Discovery");
        try {
            discover();
        } catch (Exception e) {
        }
    }

    @Override
    protected void startScan() {
        // logger.debug("Start Scan");
        if (this.scanTask != null) {
            scanTask.cancel(true);
        }
        this.scanTask = scheduler.schedule(() -> {
            try {
                discover();
            } catch (Exception e) {
            }
        }, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void stopScan() {
        // logger.debug("Stop Scan");
        super.stopScan();

        if (this.scanTask != null) {
            this.scanTask.cancel(true);
            this.scanTask = null;
        }
    }

    private void discover() {
        logger.debug("Start Discovery");
        List<StructureList> structureList = new ArrayList<StructureList>();
        List<Root> structures = new ArrayList<Root>();
        ThingUID bridgeUID = accountHandler.getThing().getUID();
        List<Thing> things = accountHandler.getThing().getThings();
        if (structures == null || structures.isEmpty()) {
            StructureList structureResponse = accountHandler.getApi().getStructureList();
            structureList.add(structureResponse);
            for (int sR = 0; sR < structureList.size(); sR++) {
                for (int sL = 0; sL < structureList.get(sR).getStructures().size(); sL++) {
                    Root structure = accountHandler.getApi()
                            .getStructure(structureList.get(sR).getStructures().get(sL).toString());
                    structures.add(structure);
                    logger.debug("Amount Of Structures: {}", structures.size());
                }
            }
        }
        for (int s = 0; s < structures.size(); s++) {
            String sId = structures.get(s).getGroupId();
            String sName = structures.get(s).getName();
            logger.debug("Structure Id {} with name {} Found", sId, sName);
            for (int d = 0; d < structures.get(s).getDevices().size(); d++) {
                String dType = structures.get(s).getDevices().get(d).getCat().toString();
                String dId = structures.get(s).getDevices().get(d).getDeviceId().toString();
                String[] array1 = dId.split("-");
                String sdId = array1[1].toString();
                Boolean deviceAdded = false;
                for (int i = 0; i < things.size(); i++) {
                    String id = things.get(i).getConfiguration().get("sdId").toString();
                    if (id.equals(sdId)) {
                        deviceAdded = true;
                    }
                }
                if (!deviceAdded) {
                    logger.debug("Device Simplified Id: {}", sdId);
                    String dProductCode = structures.get(s).getDevices().get(d).getProductCode().toString();
                    logger.debug("Product Code discovered: {}", "'" + dProductCode + "'");
                    switch (dProductCode) {
                        case "L2":
                            thingTypeUid = THING_TYPE_LIGHTWAVE_HUB;
                            break;
                        case "LW931":
                            thingTypeUid = THING_TYPE_MAGNETIC_GEN1;
                            break;
                        case "LW600":
                            thingTypeUid = THING_TYPE_EMONITOR_GEN1;
                            break;
                        case "LW320":
                        case "LW380":
                        case "LW260":
                            thingTypeUid = THING_TYPE_SSOCKET_GEN1;
                            break;
                        case "LW381":
                            thingTypeUid = THING_TYPE_DSOCKET_GEN1;
                            break;
                        case "LW400":
                            thingTypeUid = THING_TYPE_SDIMMER_GEN1;
                            break;
                        case "L42":
                            thingTypeUid = THING_TYPE_DSOCKET_GEN2;
                            break;
                        case "L21":
                        case "L21MK2":
                            thingTypeUid = THING_TYPE_SDIMMER_GEN2;
                            break;
                        case "L22":
                        case "L22MK2":
                        case "L22EU":
                            thingTypeUid = THING_TYPE_DDIMMER_GEN2;
                            break;
                        case "L23":
                        case "L23MK2":
                            thingTypeUid = THING_TYPE_TDIMMER_GEN2;
                            break;
                        case "L24":
                        case "L24MK2":
                            thingTypeUid = THING_TYPE_QDIMMER_GEN2;
                            break;
                        case "LW921":
                            thingTypeUid = THING_TYPE_THERMOSTAT;
                            break;
                        case "LW922":
                            thingTypeUid = THING_TYPE_TRV;
                            break;
                        case "LW821":
                            thingTypeUid = THING_TYPE_TRELAY_GEN1;
                            break;
                        default:
                            thingTypeUid = THING_TYPE_UNKNOWNDEVICE;
                    }
                    logger.debug("Used thingTypeUID: {}", thingTypeUid.toString());
                    ThingUID deviceThing = new ThingUID(thingTypeUid, accountHandler.getThing().getUID(), sdId);
                    Map<String, Object> dProperties = new HashMap<>();
                    dProperties.put("Device ID", dId);
                    dProperties.put("sdId", sdId);
                    dProperties.put("Name", structures.get(s).getDevices().get(d).getName().toString());
                    dProperties.put("Device", structures.get(s).getDevices().get(d).getDevice().toString());
                    dProperties.put("Type", dType);
                    dProperties.put("Description", structures.get(s).getDevices().get(d).getDesc().toString());
                    dProperties.put("Product", structures.get(s).getDevices().get(d).getProduct().toString());
                    dProperties.put("Product Code", structures.get(s).getDevices().get(d).getProductCode().toString());
                    dProperties.put("Category", structures.get(s).getDevices().get(d).getCat().toString());
                    dProperties.put("Generation", structures.get(s).getDevices().get(d).getGen().toString());
                    dProperties.put("Channels", structures.get(s).getDevices().get(d).getFeatureSets().size());
                    String label = createLabelDevice(structures.get(s).getDevices().get(d));
                    thingDiscovered(
                            DiscoveryResultBuilder.create(deviceThing).withLabel(label).withProperties(dProperties)
                                    .withRepresentationProperty(sdId.toString()).withBridge(bridgeUID).build());
                }
            }
        }
    }

    public String createLabelDevice(Devices deviceList) {
        StringBuilder sb = new StringBuilder();
        sb.append(deviceList.getName());
        return sb.toString();
    }

    @Override
    public void setThingHandler(ThingHandler handler) {
        if (handler instanceof LWAccountHandler) {
            accountHandler = (LWAccountHandler) handler;
        }
    }

    @Override
    public ThingHandler getThingHandler() {
        return accountHandler;
    }
}
