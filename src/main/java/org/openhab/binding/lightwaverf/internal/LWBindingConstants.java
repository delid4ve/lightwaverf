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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.openhab.binding.lightwaverf.internal.api.discovery.*;
import org.openhab.binding.lightwaverf.internal.api.FeatureStatus;

/**
 * The {@link lightwaverfBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author David Murton - Initial contribution
 */

@NonNullByDefault
public class LWBindingConstants {
        public static List<Root> structures = new ArrayList<Root>();
        public static List<Devices> devices = new ArrayList<Devices>();
        public static List<FeatureSets> featureSets = new ArrayList<FeatureSets>();
        public static List<Features> features = new ArrayList<Features>();
        public static List<FeatureStatus> featureStatus = new ArrayList<FeatureStatus>();
        public static List<List<FeatureStatus>> partitions = new ArrayList<>();


    private static final String BINDING_ID = "lightwaverf";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_ACCOUNT = new ThingTypeUID(BINDING_ID, "lightwaverfaccount");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_STRUCTURE = new ThingTypeUID(BINDING_ID, "structure");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_DEVICE = new ThingTypeUID(BINDING_ID, "device");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_SOCKET = new ThingTypeUID(BINDING_ID, "socket");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_THERMOSTAT = new ThingTypeUID(BINDING_ID, "thermostat");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_DIMMER = new ThingTypeUID(BINDING_ID, "dimmer");
    public static final ThingTypeUID THING_TYPE_LIGHTWAVE_HUB = new ThingTypeUID(BINDING_ID, "hub");
    
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPE_UIDS = Collections
            .unmodifiableSet(Stream.of(THING_TYPE_LIGHTWAVE_ACCOUNT, THING_TYPE_LIGHTWAVE_STRUCTURE,
                    THING_TYPE_LIGHTWAVE_DEVICE, THING_TYPE_LIGHTWAVE_SOCKET ,
                    THING_TYPE_LIGHTWAVE_THERMOSTAT, THING_TYPE_LIGHTWAVE_DIMMER,
                    THING_TYPE_LIGHTWAVE_HUB).collect(Collectors.toSet()));

    public static final Set<ThingTypeUID> DISCOVERABLE_THING_TYPE_UIDS = Collections.unmodifiableSet(
            Stream.of(THING_TYPE_LIGHTWAVE_STRUCTURE, THING_TYPE_LIGHTWAVE_DEVICE,
            THING_TYPE_LIGHTWAVE_SOCKET, THING_TYPE_LIGHTWAVE_THERMOSTAT, THING_TYPE_LIGHTWAVE_DIMMER,
            THING_TYPE_LIGHTWAVE_HUB)
                    .collect(Collectors.toSet()));

    // Common channels
    public static final String CHANNEL_DISCOVER_DEVICES = "discoverDevices";
    public static final String CHANNEL_DISCOVER_STRUCTURES = "discoverStructures";
    // Structure / Devices / Featuresets
    public static final String CHANNEL_NAME = "name";
    // Devices / Features
    public static final String CHANNEL_TYPE = "type";

    // List of all Channel ids (Sockets)
    public static final String CHANNEL_SWITCH = "switch";
    public static final String CHANNEL_OUTLET_IN_USE = "outletInUse";
    public static final String CHANNEL_PROTECTION = "protection";
    public static final String CHANNEL_POWER = "power";
    public static final String CHANNEL_ENERGY = "energy";
    public static final String CHANNEL_IDENTIFY = "identify";
    public static final String CHANNEL_RESET = "reset";
    public static final String CHANNEL_UPGRADE = "upgrade";
    public static final String CHANNEL_DIAGNOSTICS = "diagnostics";
    public static final String CHANNEL_PERIOD_OF_BROADCAST = "periodOfBroadcast";
    public static final String CHANNEL_RGB_COLOR = "rgbColor";
    public static final String CHANNEL_VOLTAGE = "voltage";
    // List of all Channel ids (Structure)

    public static final String CHANNEL_GROUP_ID = "groupId";
    // List of all Channel ids (Devices)
    public static final String CHANNEL_DEVICE_ID = "deviceId";
    public static final String CHANNEL_PRODUCT_CODE = "productCode";
    public static final String CHANNEL_PRODUCT = "product";
    public static final String CHANNEL_DEVICE = "device";
    public static final String CHANNEL_DESC = "desc";

    public static final String CHANNEL_CAT = "cat";
    public static final String CHANNEL_GEN = "gen";
    // List of all Channel ids (Featuresets)
    public static final String CHANNEL_FEATURE_SET_ID = "featureSetId";
    // List of all Channel ids (Features)
    public static final String CHANNEL_FEATURE_ID = "featureId";
    public static final String CHANNEL_WRITABLE = "writable";

    public static final String CHANNEL_BATTERY_LEVEL = "batteryLevel";
    public static final String CHANNEL_RSSI = "rssi";
    public static final String CHANNEL_CALL_FOR_HEAT = "callForHeat";
    public static final String CHANNEL_TEMPERATURE = "temperature";
    public static final String CHANNEL_TARGET_TEMPERATURE = "targetTemperature";
    public static final String CHANNEL_VALVE_LEVEL = "valveLevel";
    public static final String CHANNEL_HEAT_STATE = "heatState";

    public static final String CHANNEL_DIM_LEVEL = "dimLevel";
    public static final String CHANNEL_DIM_SETUP = "dimSetup";
    public static final String CHANNEL_BULB_SETUP = "bulbSetup";

    public static final String CHANNEL_CURRENT_TIME = "currentTime";
    public static final String CHANNEL_BUTTON_PRESS = "buttonPress";
    public static final String CHANNEL_TIME = "time";
    public static final String CHANNEL_DATE = "date";
    public static final String CHANNEL_MONTH_ARRAY = "monthArray";
    public static final String CHANNEL_WEEKDAY_ARRAY = "wekdayArray";
    public static final String CHANNEL_LOCATION_LONGITUDE = "locationLongitude";
    public static final String CHANNEL_LOCATION_LATITUDE = "locationLatitude";
    public static final String CHANNEL_DUSK_TIME = "duskTime";
    public static final String CHANNEL_DAWN_TIME = "dawnTime";
    public static final String CHANNEL_DAY = "day";
    public static final String CHANNEL_MONTH = "month";
    public static final String CHANNEL_YEAR = "year";
    public static final String CHANNEL_WEEKDAY = "weekday";
    public static final String CHANNEL_TIME_ZONE = "timeZone";
}
