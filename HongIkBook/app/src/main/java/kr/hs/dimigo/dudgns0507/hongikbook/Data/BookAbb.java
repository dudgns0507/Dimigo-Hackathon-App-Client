package kr.hs.dimigo.dudgns0507.hongikbook.Data;

/**
 * Created by pyh42 on 2016-12-10.
 */

public class BookAbb {
    private String id;

    private boolean rental_state;

    private String author;

    private String title;

    private String _id;

    private boolean rental;

    private String publisher;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public boolean getRental_state ()
    {
        return rental_state;
    }

    public void setRental_state (boolean rental_state)
    {
        this.rental_state = rental_state;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public boolean getRental ()
    {
        return rental;
    }

    public void setRental (boolean rental)
    {
        this.rental = rental;
    }

    public String getPublisher ()
    {
        return publisher;
    }

    public void setPublisher (String publisher)
    {
        this.publisher = publisher;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", rental_state = "+rental_state+", author = "+author+", title = "+title+", _id = "+_id+", rental = "+rental+", publisher = "+publisher+"]";
    }
}
