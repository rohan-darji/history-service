package edu.iu.rdarji.historyservice.rabbitmq;

import edu.iu.rdarji.historyservice.model.PrimesRecord;
import edu.iu.rdarji.historyservice.repository.PrimesHistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Component
public class MQReceiver {
    private final PrimesHistoryRepository primesHistoryRepository;

    public MQReceiver(PrimesHistoryRepository primesHistoryRepository) {
        this.primesHistoryRepository = primesHistoryRepository;
    }

    @RabbitListener(queues = {"primes"})
    public void receiveMessage(@Payload String message) {
        System.out.println(message);
        Gson gson = new Gson();
        PrimesRecord primesRecord = gson.fromJson(message, PrimesRecord.class);
        primesHistoryRepository.save(primesRecord);
    }
}
