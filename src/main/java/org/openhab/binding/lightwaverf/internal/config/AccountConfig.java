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
package org.openhab.binding.lightwaverf.internal.config;

/**
 * The {@link lightwaverfBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author David Murton - Initial contribution
 */

public class AccountConfig {

    public String username;
    public String password;
    public int pollingInterval;
    public int pollingGroupSize;
    public int electricityCost;

    

    @Override
    public String toString() {
        return "[username=" + username + ", password=" + getPasswordForPrinting()  +
        ", pollingInterval=" + pollingInterval + ", pollingGroupSize=" + pollingGroupSize + 
        ", electricityCost=" + electricityCost +"]";
    }

    private String getPasswordForPrinting() {
        if (password != null) {
            return password.isEmpty() ? "<empty>" : "*********";
        }
        return "<null>";
    }
}
