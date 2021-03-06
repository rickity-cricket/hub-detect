/**
 * hub-detect
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.hub.detect.model;

import com.blackducksoftware.integration.hub.bdio.graph.DependencyGraph;
import com.blackducksoftware.integration.hub.bdio.model.externalid.ExternalId;
import com.blackducksoftware.integration.hub.detect.codelocation.CodeLocationName;
import com.blackducksoftware.integration.hub.detect.codelocation.CodeLocationNameService;

public class DetectCodeLocation {
    private final BomToolType bomToolType;
    private final String sourcePath;
    private final String bomToolProjectName;
    private final String bomToolProjectVersionName;
    private final String dockerImage;
    private final ExternalId bomToolProjectExternalId;
    private final DependencyGraph dependencyGraph;

    public static class Builder {
        private final BomToolType bomToolType;
        private final String sourcePath;
        private String bomToolProjectName;
        private String bomToolProjectVersionName;
        private String dockerImage;
        private final ExternalId bomToolProjectExternalId;
        private final DependencyGraph dependencyGraph;

        public Builder(final BomToolType bomToolType, final String sourcePath, final ExternalId bomToolProjectExternalId, final DependencyGraph dependencyGraph) {
            this.bomToolType = bomToolType;
            this.sourcePath = sourcePath;
            this.bomToolProjectExternalId = bomToolProjectExternalId;
            this.dependencyGraph = dependencyGraph;
        }

        public Builder bomToolProjectName(final String bomToolProjectName) {
            this.bomToolProjectName = bomToolProjectName;
            return this;
        }

        public Builder bomToolProjectVersionName(final String bomToolProjectVersionName) {
            this.bomToolProjectVersionName = bomToolProjectVersionName;
            return this;
        }

        public Builder dockerImage(final String dockerImage) {
            this.dockerImage = dockerImage;
            return this;
        }

        public DetectCodeLocation build() {
            return new DetectCodeLocation(this);
        }
    }

    private DetectCodeLocation(final Builder builder) {
        this.bomToolType = builder.bomToolType;
        this.sourcePath = builder.sourcePath;
        this.bomToolProjectName = builder.bomToolProjectName;
        this.bomToolProjectVersionName = builder.bomToolProjectVersionName;
        this.dockerImage = builder.dockerImage;
        this.bomToolProjectExternalId = builder.bomToolProjectExternalId;
        this.dependencyGraph = builder.dependencyGraph;
    }

    public CodeLocationName createCodeLocationName(final CodeLocationNameService codeLocationNameService, final String projectName, final String projectVersionName, final String prefix, final String suffix) {
        if (BomToolType.DOCKER == getBomToolType()) {
            return codeLocationNameService.createDockerName(getSourcePath(), projectName, projectVersionName, dockerImage, getBomToolType(), prefix, suffix);
        } else {
            return codeLocationNameService.createBomToolName(getSourcePath(), projectName, projectVersionName, getBomToolType(), prefix, suffix);
        }
    }

    public String getCodeLocationNameString(final CodeLocationNameService codeLocationNameService, final CodeLocationName codeLocationName) {
        if (BomToolType.DOCKER == getBomToolType()) {
            return codeLocationNameService.generateDockerCurrent(codeLocationName);
        } else {
            return codeLocationNameService.generateBomToolCurrent(codeLocationName);
        }
    }

    public BomToolType getBomToolType() {
        return bomToolType;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getBomToolProjectName() {
        return bomToolProjectName;
    }

    public String getBomToolProjectVersionName() {
        return bomToolProjectVersionName;
    }

    public String getDockerImage() {
        return dockerImage;
    }

    public ExternalId getBomToolProjectExternalId() {
        return bomToolProjectExternalId;
    }

    public DependencyGraph getDependencyGraph() {
        return dependencyGraph;
    }

}
