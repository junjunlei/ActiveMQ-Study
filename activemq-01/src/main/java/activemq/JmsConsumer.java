package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Jerry
 * @create 2019-09-17 14:59
 */
public class JmsConsumer {
    private static  final String ACTIVEMQ_URL="tcp://服务器地址:61616";
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
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        while(true){
            //receive 注意时间
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if(textMessage!=null){
                System.out.println("消费者接收到的消息为："+textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();

    }
}
