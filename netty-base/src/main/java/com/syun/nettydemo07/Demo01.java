package com.syun.nettydemo07;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.reactivestreams.Publisher;
import org.testng.annotations.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.NettyPipeline;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import reactor.netty.http.server.HttpServerRoutes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/*
 * @description:
 * @program: netty-base
 * @author: syun
 * @create: 2019-07-29 11:23
 */
public class Demo01 {

    @Test
    public void httpServer() throws InterruptedException {
        HttpServer.create()
                .host("127.0.0.1")
                .port(8080)
                .route(routes)
                .bindNow()
                .onDispose()
                .block();
    }

    private Consumer<HttpServerRoutes> routes = routes ->
            routes.get("/hello", (request, response) -> response.sendString(Mono.just("Hello World!")))
                    .post("/echo", (request, response) -> response.send(request.receive().retain()))
                    .get("/path/{param}", (request, response) -> response.sendString(Mono.just(Objects.requireNonNull(request.param("param")))))
                    .ws("/ws", (wsInbound, wsOutbound) -> wsOutbound.send(wsInbound.receive().retain()))
                    .get("/test", (request, response) ->
                            response
//                            .status(HttpResponseStatus.OK)
//                            .header(HttpHeaderNames.CONTENT_LENGTH, "12")
                                    .sendString(Mono.just("hello world").delayElement(Duration.ofSeconds(5)).log()))
                    .file("/", "src/main/resources/index.html")  // 静态页面
                    .get("/clientIp", ((httpServerRequest, httpServerResponse) ->
                            httpServerResponse.sendString(Mono.just(httpServerRequest.remoteAddress().getHostString()))));




    //    server-sent events
    @Test
    public void SseServer() {

        DisposableServer server =
                HttpServer.create()
                        .host("127.0.0.1")
                        .port(8080)
                        .route(routes -> routes.get("/see", serverSse()))
                        .bindNow();
        server.onDispose()
                .block();
    }


    private static BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> serverSse() {
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));
        return (request, response) -> response.sse()
                .options(NettyPipeline.SendOptions::flushOnEach)
                .send(flux.map(p -> toByteBuf(p)));

    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static ByteBuf toByteBuf(Object any) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write("data".getBytes(Charset.defaultCharset()));
            MAPPER.writeValue(out, any);
            out.write("\n\n".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ByteBufAllocator.DEFAULT
                .buffer()
                .writeBytes(out.toByteArray());
    }



}
