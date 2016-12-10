package kr.hs.dimigo.dudgns0507.hongikbook.Data;

/**
 * Created by pyh42 on 2016-12-10.
 */

public class BookFull {
    private String owner_serial;

    private String ISBN;

    private String rental_date;

    private String image_url;

    private String rental_extension;

    private String type;

    private String rental;

    private String publication;

    private String publisher;

    private String id;

    private String rental_extension_state;

    private String author;

    private String rental_state;

    private String title;

    private String price;

    private String _id;

    private String description;

    private String createDate;

    public String getOwner_serial ()
    {
        return owner_serial;
    }

    public void setOwner_serial (String owner_serial)
    {
        this.owner_serial = owner_serial;
    }

    public String getISBN ()
    {
        return ISBN;
    }

    public void setISBN (String ISBN)
    {
        this.ISBN = ISBN;
    }

    public String getRental_date ()
    {
        return rental_date;
    }

    public void setRental_date (String rental_date)
    {
        this.rental_date = rental_date;
    }

    public String getRental_extension ()
    {
        return rental_extension;
    }

    public void setRental_extension (String rental_extension)
    {
        this.rental_extension = rental_extension;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getRental ()
    {
        return rental;
    }

    public void setRental (String rental)
    {
        this.rental = rental;
    }

    public String getPublication ()
    {
        return publication;
    }

    public void setPublication (String publication)
    {
        this.publication = publication;
    }

    public String getPublisher ()
    {
        return publisher;
    }

    public void setPublisher (String publisher)
    {
        this.publisher = publisher;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getRental_extension_state ()
    {
        return rental_extension_state;
    }

    public void setRental_extension_state (String rental_extension_state)
    {
        this.rental_extension_state = rental_extension_state;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getRental_state ()
    {
        return rental_state;
    }

    public void setRental_state (String rental_state)
    {
        this.rental_state = rental_state;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getCreateDate ()
    {
        return createDate;
    }

    public void setCreateDate (String createDate)
    {
        this.createDate = createDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [owner_serial = "+owner_serial+", ISBN = "+ISBN+", rental_date = "+rental_date+", rental_extension = "+rental_extension+", type = "+type+", rental = "+rental+", publication = "+publication+", publisher = "+publisher+", id = "+id+", rental_extension_state = "+rental_extension_state+", author = "+author+", rental_state = "+rental_state+", title = "+title+", price = "+price+", _id = "+_id+", description = "+description+", createDate = "+createDate+"]";
    }
}
