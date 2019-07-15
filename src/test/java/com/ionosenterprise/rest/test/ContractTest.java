/*
 * Copyright (c) 2017, 1&1 IONOS Cloud GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by the <organization>.
 * 4. Neither the name of the 1&1 IONOS Cloud nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY 1&1 IONOS Cloud GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL 1&1 IONOS Cloud GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ionosenterprise.rest.test;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Contract;
import com.ionosenterprise.rest.domain.User;
import com.ionosenterprise.rest.test.resource.UserResource;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author denis@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContractTest extends BaseTest {

    @Test
    public void t1_testGetContract() throws RestClientException, IOException, IllegalArgumentException {
        Contract contract = ionosEnterpriseApi.getContract().getContract();
        assertNotNull(contract);
    }

    @Test
    public void t2_testContractDetailsForNonAdminUser() throws Exception {
        Contract contractAsOwner = ionosEnterpriseApi.getContract().getContract();
        assertNotNull(contractAsOwner);
        assertNotNull(contractAsOwner.getProperties());
        assertNotNull(contractAsOwner.getProperties().getResourceLimits());
        assertNotNull(contractAsOwner.getProperties().getResourceLimits().getCoresPerServer());
        assertNotNull(contractAsOwner.getProperties().getResourceLimits().getRamPerServer());


        User user = UserResource.getUser();
        user.getProperties().setAdministrator(false);
        User newUser = ionosEnterpriseApi.getUser().createUser(user);
        assertNotNull(newUser);
        waitTillProvisioned(newUser.getRequestId());

        setIonosEnterpriseApiCredentials(user.getProperties().getEmail(), user.getProperties().getPassword());

        Contract contractAsNonAdminUser = ionosEnterpriseApi.getContract().getContract();
        assertNotNull(contractAsNonAdminUser);
        assertNotNull(contractAsNonAdminUser.getProperties());
        assertNotNull(contractAsNonAdminUser.getProperties().getResourceLimits());
        assertNotNull(contractAsNonAdminUser.getProperties().getResourceLimits().getCoresPerContract());
        assertEquals(
                contractAsNonAdminUser.getProperties().getResourceLimits().getCoresPerServer(),
                contractAsOwner.getProperties().getResourceLimits().getCoresPerServer()
        );
        assertNotNull(contractAsNonAdminUser.getProperties().getResourceLimits().getRamPerContract());
        assertEquals(
                contractAsNonAdminUser.getProperties().getResourceLimits().getRamPerServer(),
                contractAsOwner.getProperties().getResourceLimits().getRamPerServer()
        );

        resetIonosEnterpriseApiCredentials();
        ionosEnterpriseApi.getUser().deleteUser(newUser.getId());
    }
}
