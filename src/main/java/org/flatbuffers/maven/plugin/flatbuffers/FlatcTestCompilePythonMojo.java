package org.flatbuffers.maven.plugin.flatbuffers;

/*
 * Copyright (c) 2016 Maven Protocol Buffers Plugin Authors. All rights reserved.
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
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;

/**
 * This mojo executes the {@code protoc} compiler for generating test python sources
 * from protocol buffer definitions. It also searches dependency artifacts in the test scope for
 * {@code .proto} files and includes them in the {@code proto_path} so that they can be
 * referenced. Finally, it adds the {@code .proto} files to the project as test resources so
 * that they can be included in the test-jar artifact.
 *
 * @since 0.3.3
 */
@Mojo(
        name = "test-compile-python",
        defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES,
        requiresDependencyResolution = ResolutionScope.TEST,
        threadSafe = true
)
public final class FlatcTestCompilePythonMojo extends AbstractFlatcTestCompileMojo {

    /**
     * This is the directory into which the {@code .py} test sources will be created.
     */
    @Parameter(
            required = true,
            property = "pythonTestOutputDirectory",
            defaultValue = "${project.build.directory}/generated-test-sources/protobuf/python"
    )
    private File outputDirectory;

    @Override
    protected void addFlatcBuilderParameters(final Flatc.Builder protocBuilder) throws MojoExecutionException {
        super.addFlatcBuilderParameters(protocBuilder);
        protocBuilder.setPythonOutputDirectory(getOutputDirectory());
        // We need to add project output directory to the protobuf import paths,
        // in case test protobuf definitions extend or depend on production ones
        final File buildOutputDirectory = new File(project.getBuild().getOutputDirectory());
        if (buildOutputDirectory.exists()) {
            protocBuilder.addFBPathElement(buildOutputDirectory);
        }
    }

    @Override
    protected File getOutputDirectory() {
        return outputDirectory;
    }
}
