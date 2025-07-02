package com.example.grpc_client;

import com.example.grpc.AddRequest;
import com.example.grpc.AddResponse;
import com.example.grpc.CalculatorServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CalculatorClient implements CommandLineRunner {


   @GrpcClient("calculator-server")
   private CalculatorServiceGrpc.CalculatorServiceStub stub;

    @Override
    public void run(String... args) throws Exception {
        AddRequest request = AddRequest.newBuilder()
            .setX(3)
            .setY(4)
            .build();

        stub.add(request, new StreamObserver<AddResponse>() {

            @Override
            public void onNext(AddResponse response) {
                System.out.println("응답 결과" + response.getResult());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("응답 종료");
            }
        });
    }
}
