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
package org.openhab.binding.lightwaverf.internal.connect.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.lightwaverf.internal.connect.utilities.AbstractLWCommand;
import org.openhab.binding.lightwaverf.internal.connect.utilities.GeneralMessageId;
import org.openhab.binding.lightwaverf.internal.connect.utilities.LWType;
import org.openhab.binding.lightwaverf.internal.connect.utilities.MessageId;
import org.openhab.binding.lightwaverf.internal.connect.utilities.MessageType;

/**
 * This represents a command to set heating to a particular value. On the LAN
 * commands are like: 104,!R1DhF*tP19.0
 *
 * @author Neil Renaud
 * @since 1.7.0
 */
public class TemperatureCommand extends AbstractLWCommand implements RoomMessage {

    private static final Pattern REG_EXP = Pattern.compile(".*?([0-9]{1,3}),!R([0-9])DhF\\*tP([0-9\\.]{1,5}).*\\s*");
    private static final String FUNCTION = "*t";
    private final MessageId messageId;
    private final String roomId;
    private final String deviceId = "h";
    private final double setTemperature;

    public TemperatureCommand(String message) {
        Matcher m = REG_EXP.matcher(message);
        m.matches();
        messageId = new GeneralMessageId(Integer.valueOf(m.group(1)));
        roomId = m.group(2);
        setTemperature = Double.valueOf(m.group(3));
    }

    public TemperatureCommand(int messageId, String roomId, double setTemperature) {
        this.messageId = new GeneralMessageId(messageId);
        this.roomId = roomId;
        this.setTemperature = setTemperature;
    }

    @Override
    public String getCommandString() {
        return getMessageString(messageId, roomId, deviceId, FUNCTION, setTemperature);
    }

    @Override
    public String getRoomId() {
        return roomId;
    }

    @Override
    public State getState(LWType type) {
        switch (type) {
            case HEATING_SET_TEMP:
                return new DecimalType(setTemperature);
            default:
                return null;
        }
    }

    @Override
    public MessageId getMessageId() {
        return messageId;
    }

    public static boolean matches(String message) {
        return message.contains(FUNCTION);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ROOM;
    }
}
