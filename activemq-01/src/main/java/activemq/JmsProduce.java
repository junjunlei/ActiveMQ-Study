package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *简单的生产者
 * @author Jerry
 * @create 2019-09-17 14:15
 */
public class JmsProduce {
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
        //6.创建消息
        TextMessage textMessage = session.createTextMessage("生产一条消息");
        //7.消息生产者发送到mq
        messageProducer.send(textMessage);
        //8.关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息成功发送到MQ");
    }
}
