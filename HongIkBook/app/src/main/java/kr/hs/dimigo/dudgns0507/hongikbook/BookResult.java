package kr.hs.dimigo.dudgns0507.hongikbook;

/**
 * Created by pyh42 on 2016-11-24.
 */

public class BookResult
{
    private Channel channel;

    public Channel getChannel ()
    {
        return channel;
    }

    public void setChannel (Channel channel)
    {
        this.channel = channel;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [channel = "+channel+"]";
    }
}