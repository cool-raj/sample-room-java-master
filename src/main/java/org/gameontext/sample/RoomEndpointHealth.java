/*******************************************************************************
 * Copyright (c) 2016 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.gameontext.sample;

import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class RoomEndpointHealth implements HealthCheck {

    @Inject
    private RoomImplementation roomImplementation;

    public boolean isHealthy() {
        if ( roomImplementation != null && roomImplementation.ok() ) {
            return true;
        }
        return false;
    }

    @Override
    public HealthCheckResponse call() {
      if (!isHealthy()) {
        return HealthCheckResponse.named(RoomDescription.class.getSimpleName())
                                  .withData("services", "not available").down()
                                  .build();
      }
      return HealthCheckResponse.named(RoomDescription.class.getSimpleName())
                                .withData("services", "available").up().build();
    }
}
