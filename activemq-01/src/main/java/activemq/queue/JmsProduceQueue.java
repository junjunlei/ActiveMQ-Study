package activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * QUEUE队列
 *简单的生产者
 * @author Jerry
 * @create 2019-09-17 14:15
 */
public class JmsProduceQueue {
    private static  final String ACTIVEMQ_URL="tcp://59.110.164.184:61616";
    private static  final String QUEUE_NAME="队列01";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂,按照指定的url地址,采用默认用户名和密码
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = factory.createConnection();
        connection.start();
        //3.闯将会话session  事务/签收
        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地   队列or主题topic
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);
        int n=6;
        for(int i=1;i<=n;i++){
        //6.创建消息
        TextMessage textMessage = session.createTextMessage("生产第"+i+"条消息");
        //7.消息生产者发送到mq
        messageProducer.send(textMessage);
        }
        //8.关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息成功发送到MQ");
    }
    /**
     * queue队列
     * 消费者三大消费情况：
     *        1.先生产         只启动1号消费者。 1号消费者能消息
     *        2.先生产         先启动1号再启动2号消费者，问题：2号消费者能消费吗？  ：不能
     *        3.先启动2个消费者，再生产6条消息，请问，如何消费？
     *             轮询，一人3条
     */
}
