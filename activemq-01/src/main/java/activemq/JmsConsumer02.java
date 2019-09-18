package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 简单的消费者--监听方式消费消息
 * @author Jerry
 * @create 2019-09-17 14:59
 */
public class JmsConsumer02 {
    private static  final String ACTIVEMQ_URL="tcp://59.110.164.184:61616";
    private static  final String QUEUE_NAME="队列01";
    public static void main(String[] args) throws JMSException, IOException {
        //1.创建连接工厂,按照指定的url地址,采用默认用户名和密码
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂，获得连接connection并启动访问
        Connection connection = factory.createConnection();
        connection.start();
        //3.闯将会话session  事务/签收
        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地   队列or主题topic
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        //通过监听方式来消费消息，异步非阻塞
        messageConsumer.setMessageListener((message)->{
            if(null!=message && message instanceof  TextMessage){
                TextMessage textMessage=(TextMessage)message;
                try {
                    System.out.println("消费者消费的消息："+textMessage.getText());
                }catch (JMSException e){
                    e.printStackTrace();
                }

            }
        });
        //保持控制台不灭
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }
}
