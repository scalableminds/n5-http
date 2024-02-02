/*-
 * #%L
 * N5 AWS S3
 * %%
 * Copyright (C) 2017 - 2022, Saalfeld Lab
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package com.scalableminds.n5.http;

import com.google.gson.GsonBuilder;
import org.janelia.saalfeldlab.n5.N5Exception;
import org.janelia.saalfeldlab.n5.N5KeyValueReader;
import org.janelia.saalfeldlab.n5.N5Reader;

/**
 * TODO: javadoc
 */
public class N5HttpReader extends N5KeyValueReader {

  /**
   * TODO: reduce number of constructors ?
   */

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   *
   * @param basePath    the base path relative to the bucket root
   * @param gsonBuilder a GsonBuilder with custom configuration.
   * @param cacheMeta   cache attribute and meta data Setting this to true avoids frequent reading
   *                    and parsing of JSON encoded attributes and other meta data that requires
   *                    accessing the store. This is most interesting for high latency backends.
   *                    Changes of cached attributes and meta data by an independent writer will not
   *                    be tracked.
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader(final String basePath, final GsonBuilder gsonBuilder, final boolean cacheMeta)
      throws N5Exception {

    super(
        new HttpKeyValueAccess(""),
        basePath,
        gsonBuilder,
        cacheMeta);

		if (!exists("/")) {
			throw new N5Exception.N5IOException("No container exists at " + basePath);
		}
  }

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   *
   * @param basePath  the base path relative to the bucket root
   * @param cacheMeta cache attribute and meta data Setting this to true avoids frequent reading and
   *                  parsing of JSON encoded attributes and other meta data that requires accessing
   *                  the store. This is most interesting for high latency backends. Changes of
   *                  cached attributes and meta data by an independent writer will not be tracked.
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader(final String basePath, final boolean cacheMeta) throws N5Exception {

    this(basePath, new GsonBuilder(), cacheMeta);
  }

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   * <p>
   * Metadata are not cached.
   *
   * @param basePath    the base path relative to the bucket root
   * @param gsonBuilder a GsonBuilder with custom configuration.
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader(final String basePath, final GsonBuilder gsonBuilder) throws N5Exception {

    this(basePath, gsonBuilder, false);
  }

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   * <p>
   * Metadata are not cached.
   *
   * @param basePath the base path relative to the bucket root
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader(final String basePath) throws N5Exception {

    this(basePath, new GsonBuilder(), false);
  }

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   * <p>
   * The n5 container root is the bucket's root.
   *
   * @param gsonBuilder a GsonBuilder with custom configuration.
   * @param cacheMeta   cache attribute and meta data Setting this to true avoids frequent reading
   *                    and parsing of JSON encoded attributes and other meta data that requires
   *                    accessing the store. This is most interesting for high latency backends.
   *                    Changes of cached attributes and meta data by an independent writer will not
   *                    be tracked.
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader(final GsonBuilder gsonBuilder, final boolean cacheMeta) throws N5Exception {

    this("/", gsonBuilder, cacheMeta);
  }

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   * <p>
   * The n5 container root is the bucket's root.
   *
   * @param cacheMeta cache attribute and meta data Setting this to true avoids frequent reading and
   *                  parsing of JSON encoded attributes and other meta data that requires accessing
   *                  the store. This is most interesting for high latency backends. Changes of
   *                  cached attributes and meta data by an independent writer will not be tracked.
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader(final boolean cacheMeta) throws N5Exception {

    this("/", new GsonBuilder(), cacheMeta);
  }

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   * <p>
   * The n5 container root is the bucket's root. Metadata are not cached.
   *
   * @param gsonBuilder a GsonBuilder with custom configuration.
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader(final GsonBuilder gsonBuilder) throws N5Exception {

    this("/", gsonBuilder, false);
  }

  /**
   * Opens an {@link N5Reader} with an HTTP(S) storage backend.
   * <p>
   * The n5 container root is the bucket's root. Metadata are not cached.
   *
   * @throws N5Exception if the reader could not be created
   */
  public N5HttpReader() throws N5Exception {

    this("/", new GsonBuilder(), false);
  }
}
