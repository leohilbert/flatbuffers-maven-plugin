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
 * This mojo executes the {@code protoc} compiler for generating main JavaNano sources
 * from protocol buffer definitions. It also searches dependency artifacts for
 * {@code .proto} files and includes them in the {@code proto_path} so that they can be
 * referenced. Finally, it adds the {@code .proto} files to the project as resources so
 * that they are included in the final artifact.
 *
 * @since 0.4.3
 */
@Mojo(
        name = "compile-javanano",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        threadSafe = true
)
public final class FlatcCompileJavaNanoMojo extends AbstractFlatcCompileMojo {

    /**
     * This is the directory into which the {@code .java} will be created.
     */
    @Parameter(
            required = true,
            property = "schemaOutputDirectory",
            defaultValue = "${project.build.directory}/generated-sources/flatbuffers/schema"
    )
    private File schemaOutputDirectory;

    @Override
    protected void addFlatcBuilderParameters(final ImmutableFlatc.Builder flatcBuilder) throws MojoExecutionException {
        super.addFlatcBuilderParameters(flatcBuilder);
        flatcBuilder.schemaOutputDirectory(getSchemaOutputDirectory());
    }

    @Override
    protected File getSchemaOutputDirectory() {
        return schemaOutputDirectory;
    }
}
