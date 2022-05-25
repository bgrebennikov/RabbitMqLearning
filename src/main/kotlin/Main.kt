import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets


fun main(){
    val factory = ConnectionFactory()
    factory.newConnection(
        "amqp://admin:25050032@0.0.0.0:15672/"
    ).use { connection ->
        connection.createChannel().use { channel ->
            channel.queueDeclare("test_queue", false, false, false, null)
            while (true){
                val message = readLine()
                message?.let {
                    if(it.isNotBlank()){
                        channel.basicPublish(
                            "",
                            QUEUE_NAME,
                            null,
                            it.toByteArray(StandardCharsets.UTF_8)
                        )
                    }
                }
            }
//            println(" [x] Sent '$message'")
        }
    }
}