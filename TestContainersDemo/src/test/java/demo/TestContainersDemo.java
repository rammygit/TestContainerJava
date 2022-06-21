package demo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.*;

/**
 * @author ram
 */
@Testcontainers
@Log
public class TestContainersDemo {

    @Container
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:7.0.1-alpine"))
            .withExposedPorts(6379).withAccessToHost(true);

    RedisClient client;

    StatefulRedisConnection<String, String> connection;
    




    @BeforeEach
    public void setUp() {

//    move the init setup for redis container here for each test



    }


    @AfterEach
    public void destroy(){

        //move connection close here
    }

    @Test
    public void testSimplePutAndGet() {

        org.testcontainers.Testcontainers.exposeHostPorts(6379);
        redis.start();
        String address = redis.getHost();
        log.info("host address == "+address);
        Integer port = redis.getFirstMappedPort();

        client = RedisClient.create(RedisURI.create(address, port));

        connection = client.connect();

        RedisCommands<String, String> commands = connection.sync();

        commands.set("test", "example");

        String retrieved = commands.get("test");

        log.info("retrived value ==== "+retrieved);
        assertThat(retrieved).isEqualTo("example");



        connection.close();
        client.shutdown();
    }

}
