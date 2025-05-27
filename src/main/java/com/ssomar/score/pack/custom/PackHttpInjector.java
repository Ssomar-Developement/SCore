package com.ssomar.score.pack.custom;


import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.pack.http.HttpByteBuf;
import com.ssomar.score.pack.http.HttpInjector;
import com.ssomar.score.pack.http.HttpRequest;
import com.ssomar.score.utils.logging.Utils;
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

        if(GeneralConfig.getInstance().isSelfHostPackDebug()) Utils.sendConsoleMsg("REQUEST URI : " + requestURI);
        if(GeneralConfig.getInstance().isSelfHostPackDebug()) Utils.sendConsoleMsg("FILE NAME : " + packSettings.getFileName());
        if(GeneralConfig.getInstance().isSelfHostPackDebug()) Utils.sendConsoleMsg("FILE PATH : " + packSettings.getFile().getAbsolutePath());

        if (requestURI.equals("/score/" + packSettings.getFileName())) {
            try {
                byte[] bytes = java.nio.file.Files.readAllBytes(packSettings.getFile().toPath());
                HttpByteBuf buf = HttpByteBuf.httpBuf(ctx);
                buf.writeStatusLine("1.1", 200, "OK");
                buf.writeHeader("Content-Length", String.valueOf(bytes.length));
                buf.writeHeader("Content-Type", "application/zip");
                buf.writeHeader("Connection", "close");
                buf.writeBytes(bytes);
                return buf;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
