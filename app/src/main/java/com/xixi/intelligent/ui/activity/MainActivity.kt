package com.xixi.intelligent.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportActivity
import com.xixi.intelligent.bean.event.MyMessageEvent
import com.xixi.intelligent.common.ARConstant
import com.xixi.intelligent.common.MData
import com.xixi.intelligent.ui.fragment.MainFragment
import com.xixi.intelligent.utils.L
import com.xixi.intelligent.utils.SPUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.greenrobot.eventbus.EventBus
import java.io.IOException
import java.util.concurrent.TimeoutException

@Route(path = ARConstant.AR_MainAct)
class MainActivity : BaseSupportActivity() {

    var factory: ConnectionFactory? = null
    val exchangeName = "TOPIC_EXCHANGE"

    override fun initView(savedInstanceState: Bundle?) {
        if (findFragment(MainFragment::class.java) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance())
        }
        setupConnectionFactory()
        startThread()
    }

    override fun getContentRes(): Int {
        return R.layout.activity_main
    }

    override fun onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return DefaultHorizontalAnimator()
    }

    /**
     * 连接设置
     */
    private fun setupConnectionFactory() {
        factory = ConnectionFactory()
        factory?.host = "118.190.37.4"
        factory?.port = 5672
        factory?.username = "admin"
        factory?.password = "tripllen@602"
    }

    private var mdDisposable: Disposable? = null
    fun startThread(){
        mdDisposable = Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                basicConsume(emitter)
            }

        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                L.e("$result")
                EventBus.getDefault().post(MyMessageEvent(MData.Event_MSG_RQ, 1))
            }
    }

    fun basicConsume(emitter: ObservableEmitter<String>){
        try {
            //连接
            val connection = factory?.newConnection()
            //通道
            val channel = connection?.createChannel()

            //声明了一个交换和一个服务器命名的队列，然后将它们绑定在一起。
            channel?.exchangeDeclare(exchangeName , "topic" , true) ;
            var queueName = channel?.queueDeclare()?.getQueue()
            channel?.queueBind(queueName , exchangeName , "*.innovent.${SPUtils.getInstance().getString("userName","*")}")

            //实现Consumer的最简单方法是将便捷类DefaultConsumer子类化。可以在basicConsume 调用上传递此子类的对象以设置订阅：
            channel?.basicConsume(queueName, false, object : DefaultConsumer(channel) {
                @Throws(IOException::class)
                override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
                    super.handleDelivery(consumerTag, envelope, properties, body)

                    val msg = String(body, Charsets.UTF_8)//接收到的消息
                    val deliveryTag = envelope.getDeliveryTag()
                    var rountingKey = envelope.getRoutingKey() ;//路由密钥
                    var contentType = properties.getContentType() ;//contentType字段，如果尚未设置字段，则为null。
                    channel.basicAck(deliveryTag, false)
                    L.i("rountingKey：$rountingKey")
                    L.i("contentType：$contentType")
                    L.i("msg：$msg")
                    L.i("deliveryTag:$deliveryTag")
                    L.i("consumerTag:$consumerTag")
                    L.i("exchangeName${envelope.getExchange()}")
                    emitter.onNext(msg)

                }
            })

        } catch (e: IOException) {
            e.printStackTrace();
        } catch (e: TimeoutException) {
            e.printStackTrace();
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    fun loginOut(){
        SPUtils.getInstance().remove("access_token")
//        SPUtils.getInstance().remove("userName")
        ARouter.getInstance().build(ARConstant.AR_LoginAct).navigation()
        finish()
    }

    override fun onDestroy() {
        if (mdDisposable != null) {
            mdDisposable!!.dispose()
        }
        super.onDestroy()
    }
}
