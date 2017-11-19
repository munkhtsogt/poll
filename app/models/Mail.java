package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name = "`MAIL`")
public class Mail extends Model{

        @Lob
        public String subject;
        
        @Lob
        public String body;
        
        @Required
        @Column(name = "`DATE`")
        public Date date = new Date();
        
        @Required
        @Column(name = "`WILLSENDDATE`")
        public Date willSendDate = new Date();
        
        @OneToOne
        public User to;
        
        @Required
        public Boolean sent = false;

}
