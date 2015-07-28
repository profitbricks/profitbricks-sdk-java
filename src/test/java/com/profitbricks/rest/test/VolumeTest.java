/*
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.profitbricks.rest.test;

import com.profitbricks.rest.client.RestClientException;
import com.profitbricks.rest.domain.DataCenter;
import com.profitbricks.rest.domain.LicenceType;
import com.profitbricks.rest.domain.raw.DataCenterRaw;
import com.profitbricks.rest.domain.Location;
import com.profitbricks.rest.domain.PBObject;
import com.profitbricks.rest.domain.Server;
import com.profitbricks.rest.domain.raw.ServerRaw;
import com.profitbricks.rest.domain.Volume;
import com.profitbricks.rest.domain.raw.VolumeRaw;
import com.profitbricks.sdk.ProfitbricksApi;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasmin.gacic
 */
public class VolumeTest {

   static ProfitbricksApi profitbricksApi = new ProfitbricksApi();
   static String dataCenterId;
   static String serverId;
   static String volumeId;
   private static String imageId;

   @BeforeClass
   public static void setUp() throws RestClientException, IOException, InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
      profitbricksApi.setCredentials("amFzbWluQHN0YWNrcG9pbnRjbG91ZC5jb206TEB4dTZFZjh6dw=");

      DataCenterRaw datacenter = new DataCenterRaw();
      datacenter.getProperties().setName("SDK TEST VOLUME - Data Center 28/7 1153");
      datacenter.getProperties().setLocation(Location.US_LAS_DEV.value());
      datacenter.getProperties().setDescription("SDK TEST Description");

      DataCenter newDatacenter = profitbricksApi.getDataCenterApi().createDataCenter(datacenter);
      dataCenterId = newDatacenter.getId();

      ServerRaw server = new ServerRaw();
      server.getProperties().setName("SDK TEST VOLUME - Server");
      server.getProperties().setRam("1024");
      server.getProperties().setCores("4");

      Server newServer = profitbricksApi.getServerApi().createServer(dataCenterId, server);

      assertNotNull(newServer);
      serverId = newServer.getId();

      VolumeRaw volume = new VolumeRaw();
      volume.getProperties().setName("SDK TEST VOLUME - Volume");
      volume.getProperties().setSize("10");
      volume.getProperties().setImage("2f5a3387-1faa-11e5-91f8-52540066fee9"); //"Ubuntu-15.04-server-2015-07-01"

      Volume newVolume = profitbricksApi.getVolumeApi().createVolume(dataCenterId, volume);
      assertNotNull(newVolume);

      volumeId = newVolume.getId();
      Thread.sleep(1000);

   }

   @AfterClass
   public static void cleanUp() throws RestClientException, IOException {
      profitbricksApi.getVolumeApi().deleteVolume(dataCenterId, volumeId);
      profitbricksApi.getServerApi().deleteServer(dataCenterId, serverId);
      profitbricksApi.getDataCenterApi().deleteDataCenter(dataCenterId);
   }

   @Test
   public void orderedTest() throws RestClientException, IOException, InterruptedException {
      testGetAllVolumes();
      Thread.sleep(45000);
      testGetVolume();
      Thread.sleep(15000);
      testAttachVolume();
      testGetAllAttachedVolumes();
      testDetachVolume();
   }

   public void testGetAllVolumes() throws RestClientException, IOException {
      List<Volume> volumes = profitbricksApi.getVolumeApi().getAllVolumes(dataCenterId);
      assertNotNull(volumes);
   }

   public void testGetAllAttachedVolumes() throws RestClientException, IOException {
      List<Volume> volumes = profitbricksApi.getVolumeApi().getAllVolumes(dataCenterId, serverId);
      assertNotNull(volumes);
   }

   public void testGetVolume() throws RestClientException, IOException, InterruptedException {
      Volume volume = profitbricksApi.getVolumeApi().getVolume(dataCenterId, volumeId);
      assertNotNull(volume);
   }

   public void testAttachVolume() throws RestClientException, IOException, InterruptedException {
      Volume attachedVolume = profitbricksApi.getVolumeApi().attachVolume(dataCenterId, serverId, volumeId);
      assertNotNull(attachedVolume);

      //Update server boot volume
      PBObject object = new PBObject();
      PBObject.BootObject bootVolume = object.new BootObject();
      bootVolume.setId(attachedVolume.getId());
      object.setBootVolume(bootVolume);

      Server server = profitbricksApi.getServerApi().updateServer(dataCenterId, serverId, object);
      assertNotNull(server);
   }

   public void testDetachVolume() throws RestClientException, IOException, InterruptedException {
      profitbricksApi.getVolumeApi().detachVolume(dataCenterId, serverId, volumeId);
   }
}
