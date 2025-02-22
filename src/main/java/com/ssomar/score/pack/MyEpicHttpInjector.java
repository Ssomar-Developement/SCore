package com.ssomar.score.pack;


import com.ssomar.score.SsomarDev;
import com.ssomar.score.pack.http.HttpByteBuf;
import com.ssomar.score.pack.http.HttpInjector;
import com.ssomar.score.pack.http.HttpRequest;
import com.ssomar.score.utils.logging.Utils;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.IOException;

public class MyEpicHttpInjector extends HttpInjector {
    @Override
    public HttpByteBuf intercept(ChannelHandlerContext ctx, HttpRequest request) {

        String requestURI = request.getRequestURI();

        if(requestURI.startsWith("/score")) {
            SsomarDev.testMsg("REQUEST URI : " + request.getRequestURI(), true);
            Utils.sendConsoleMsg("REQUEST URI : " + request.getRequestURI());

            String path = "C:\\serveur local 1.19\\plugins\\MyFurniture\\__textures__\\last_pack.zip";
            File file = new File(path);
            requestURI = requestURI.replace("/score/", "");
            String fileName = file.getName().replace(".zip", "");

            SsomarDev.testMsg("REQUEST URI : " + requestURI, true);
            SsomarDev.testMsg("FILE NAME : " + fileName, true);

            if(requestURI.equals(fileName)) {
                try {
                    byte[] bytes = java.nio.file.Files.readAllBytes(file.toPath());
                    HttpByteBuf buf = HttpByteBuf.httpBuf(ctx);
                    buf.writeStatusLine("1.1", 200, "OK");
                    buf.writeBytes(bytes);
                    return buf;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            HttpByteBuf buf = HttpByteBuf.httpBuf(ctx);
            buf.writeStatusLine("1.1", 200, "OK");
            buf.writeText("Hello, from Minecraft!");
            return buf;
        }
        return null;
    }
}
