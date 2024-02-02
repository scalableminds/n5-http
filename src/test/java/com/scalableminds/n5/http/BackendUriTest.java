package com.scalableminds.n5.http;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.janelia.saalfeldlab.n5.LockedChannel;
import org.junit.Test;

public class BackendUriTest {

  @Test
  public void testHttpUris() throws URISyntaxException {
    final HttpKeyValueAccess kv = new HttpKeyValueAccess("https://static.webknossos.org/data");
    assertEquals("https://static.webknossos.org/data/l4_sample/color",
        kv.uri(kv.compose("l4_sample", "color")).toString());
    assertEquals("https://static.webknossos.org/data/l4_sample/segmentation",
        kv.uri(kv.compose("l4_sample", "color", "..", "segmentation")).toString());
    assertEquals("https://static.webknossos.org/data/l4_sample/color",
        kv.uri(kv.compose("l4_sample", "color/")).toString());
  }

  @Test
  public void testGetData() throws IOException {
    final HttpKeyValueAccess kv = new HttpKeyValueAccess("https://static.webknossos.org/data");
    try (LockedChannel channel = kv.lockForReading("l4_sample/color/.zgroup")) {
      InputStream stream = channel.newInputStream();
      assertEquals("{\n    \"zarr_format\": \"2\"\n}", readString(stream));
    }
  }

  private String readString(InputStream inputStream) {
    return new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining("\n"));
  }
}
