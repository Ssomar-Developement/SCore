package com.ssomar.score.pack.custom;


import com.ssomar.score.pack.http.HttpByteBuf;
import com.ssomar.score.pack.http.HttpInjector;
import com.ssomar.score.pack.http.HttpRequest;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class PackHttpInjector extends HttpInjector {

    private PackSettings packSettings;

    public PackHttpInjector(PackSettings packSettings) {
        this.packSettings = packSettings;
    }

    @Override
    public HttpByteBuf intercept(ChannelHandlerContext ctx, HttpRequest request) {

        String requestURI = request.getRequestURI();

        //SsomarDev.testMsg("REQUEST URI : " + requestURI, true);
        //SsomarDev.testMsg("FILE NAME : " + packSettings.getFileName(), true);

        if (requestURI.equals("/score/" + packSettings.getFileName())) {
            try {
                byte[] bytes = java.nio.file.Files.readAllBytes(packSettings.getFile().toPath());
                HttpByteBuf buf = HttpByteBuf.httpBuf(ctx);
                buf.writeStatusLine("1.1", 200, "OK");
                buf.writeBytes(bytes);
                return buf;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
