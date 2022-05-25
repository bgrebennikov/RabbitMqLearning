import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import java.nio.charset.StandardCharsets

const val QUEUE_NAME = "test_qd1"

fun main(){
    val factory = ConnectionFactory()
    val connection = factory.newConnection(
        "amqp://admin:25050032@0.0.0.0:15672/"
    )
    val channel = connection.createChannel()
    val consumerTag = "SimpleConsumer"

    channel.queueDeclare("test_queue", false, false, false, null)

    println("[$consumerTag] Waiting for messages...")
    val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println("[$consumerTag] Received message: '$message'")
    }
    val cancelCallback = CancelCallback { consumerTag: String? ->
        println("[$consumerTag] was canceled")
    }

    channel.basicConsume(QUEUE_NAME, true, consumerTag, deliverCallback, cancelCallback)
}