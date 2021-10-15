package com.jenkov.nioserver;

/**
 * 理进来的message，并且把返回的message（包含socketId）用writeProxy enque起来
 *<P>
 * Created by jjenkov on 16-10-2015.
 */
public interface IMessageProcessor {

    public void process(Message message, WriteProxy writeProxy);

}
