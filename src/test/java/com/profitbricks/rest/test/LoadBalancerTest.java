/*
 * Copyright (c) 2017, ProfitBricks GmbH
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
 * 4. Neither the name of the ProfitBricks nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ProfitBricks GmbH ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ProfitBricks GmbH BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.*;

import static com.profitbricks.rest.test.DatacenterTest.waitTillProvisioned;

import com.profitbricks.rest.test.resource.CommonResource;
import com.profitbricks.rest.test.resource.DataCenterResource;
import com.profitbricks.rest.test.resource.NicResource;
import com.profitbricks.rest.test.resource.ServerResource;
import com.profitbricks.sdk.ProfitbricksApi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.AfterClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author jasmin@stackpointcloud.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoadBalancerTest {

    static ProfitbricksApi profitbricksApi;

    static {
        try {
            profitbricksApi = new ProfitbricksApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dataCenterId;
    static String loadBalancerId;
    static String serverId;
    static String nicId;

    @BeforeClass
    public static void setUp() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        profitbricksApi.setCredentials(System.getenv("PROFITBRICKS_USERNAME"), System.getenv("PROFITBRICKS_PASSWORD"));

        DataCenter newDatacenter = profitbricksApi.getDataCenter().createDataCenter(DataCenterResource.getDataCenter());
        dataCenterId = newDatacenter.getId();
        waitTillProvisioned(newDatacenter.getRequestId());

        LoadBalancer loadBalancer = new LoadBalancer();
        LoadBalancer.Properties properties = new LoadBalancer().new Properties();
        properties.setName("SDK TEST LOADBALANCER - LoadBalancer");
        properties.setIp("123.123.123.125");
        loadBalancer.setProperties(properties);

        LoadBalancer newLoadBalancer = profitbricksApi.getLoadbalancer().createLoadBalancer(dataCenterId, loadBalancer);
        assertNotNull(newLoadBalancer);
        waitTillProvisioned(newLoadBalancer.getRequestId());

        loadBalancerId = newLoadBalancer.getId();

        Server newServer = profitbricksApi.getServer().createServer(dataCenterId, ServerResource.getServer());
        assertNotNull(newServer);
        waitTillProvisioned(newServer.getRequestId());
        serverId = newServer.getId();

        Nic newNic = profitbricksApi.getNic().createNic(dataCenterId, serverId, NicResource.getNic());
        waitTillProvisioned(newNic.getRequestId());
        assertNotNull(newNic);
        nicId = newNic.getId();
    }

    @Test
    public void t1_getAllLoadBalancers() throws RestClientException, IOException {
        LoadBalancers loadbalancers = profitbricksApi.getLoadbalancer().getAllLoadBalancers(dataCenterId);
        assertNotNull(loadbalancers);
    }

    @Test
    public void t2_getLoadBalancer() throws RestClientException, IOException {
        LoadBalancer loadBalancer = profitbricksApi.getLoadbalancer().getLoadBalancer(dataCenterId, loadBalancerId);
        assertNotNull(loadBalancer);
        assertEquals(loadBalancerId, loadBalancer.getId());
    }

    @Test
    public void t3_updateLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LoadBalancer.Properties object = new LoadBalancer().new Properties();
        object.setName("SDK TEST LOADBALANCER - LoadBalancer - Changed");
        LoadBalancer loadBalancer = profitbricksApi.getLoadbalancer().updateLoadBalancer(dataCenterId, loadBalancerId, object);
        assertNotNull(loadBalancer);
        assertEquals(object.getName(), loadBalancer.getProperties().getName());
    }

    @Test
    public void t4_assignNicToLoadBalancer() throws RestClientException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Nic nic = profitbricksApi.getNic().assignNicToLoadBalancer(dataCenterId, loadBalancerId, nicId);
        assertNotNull(nic);
        assertEquals(nic.getId(), nicId);
        waitTillProvisioned(nic.getRequestId());
    }

    @Test
    public void t5_listAssignedNics() throws RestClientException, IOException {
        Nics nics = profitbricksApi.getNic().getAllBalancedNics(dataCenterId, loadBalancerId);
        assertNotNull(nics);
    }

    @Test
    public void t6_listAssignedNic() throws RestClientException, IOException {
        Nic nic = profitbricksApi.getNic().getBalancedNic(dataCenterId, loadBalancerId, serverId, nicId);
        assertNotNull(nic);
    }

    @Test
    public void t7_deleteLoadBalancer() throws RestClientException, IOException {
        profitbricksApi.getLoadbalancer().deleteLoadBalaner(dataCenterId, loadBalancerId);
    }

    @Test
    public void t8_getFaildLoadBalancer() throws RestClientException, IOException {
        try {
            LoadBalancer loadBalancer = profitbricksApi.getLoadbalancer().getLoadBalancer(dataCenterId, CommonResource.getBadId());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 404);
        }
    }

    @Test
    public void t9_createFailLoadBalancer() throws RestClientException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        try {
            LoadBalancer newLoadBalancer = profitbricksApi.getLoadbalancer().createLoadBalancer(dataCenterId, new LoadBalancer());
        } catch (RestClientException ex) {
            assertEquals(ex.response().getStatusLine().getStatusCode(), 422);
        }
    }


    @AfterClass
    public static void cleanup() throws RestClientException, IOException {
        profitbricksApi.getDataCenter().deleteDataCenter(dataCenterId);
    }
}
