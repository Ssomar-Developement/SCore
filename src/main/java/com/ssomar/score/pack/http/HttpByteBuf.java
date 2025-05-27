package com.ssomar.score.pack.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * A custom ByteBuf used for sending HTTP responses using HttpInterceptor.
 */
public class HttpByteBuf {
    private final ByteBuf inner;

    public HttpByteBuf(ByteBuf inner) {
        this.inner = inner;
    }

    /**
     * Writes the status line to the buffer.
     * Format is as following:
     * HTTP/{protocolVersion} {statusCode} {statusMessage} \n
     */
    public void writeStatusLine(String protocolVersion, int statusCode, String statusMessage) {
        inner.writeCharSequence("HTTP/" + protocolVersion + " " + statusCode + " " + statusMessage + "\r\n", StandardCharsets.US_ASCII);
    }

    public void writeHeader(String header, String value) {
        inner.writeCharSequence(header + ": " + value + "\r\n", StandardCharsets.US_ASCII);
    }

    public void writeText(String text) {
        inner.writeCharSequence("\r\n" + text, StandardCharsets.US_ASCII);
    }

    public void writeBytes(byte[] bytes) {
        inner.writeCharSequence("\r\n", StandardCharsets.US_ASCII); // ends headers
        inner.writeBytes(bytes); // then writes body
    }

    /**
     * @return the inner byte buf
     */
    public ByteBuf inner() {
        return inner;
    }

    /**
     * Creates a new {@link HttpByteBuf} based off a {@link ChannelHandlerContext}'s allocator.
     *
     * @param ctx the context
     * @return the HTTP byte buf
     */
    public static HttpByteBuf httpBuf(ChannelHandlerContext ctx) {
        return new HttpByteBuf(ctx.alloc().buffer());
    }
}
