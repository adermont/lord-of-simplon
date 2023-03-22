module fr.simplon.lordofsimplon {
    exports fr.simplon.lordofsimplon.impl.fight;
    exports fr.simplon.lordofsimplon.impl.jpa;
    exports fr.simplon.lordofsimplon.impl.heroes;
    exports fr.simplon.lordofsimplon.impl.persist;
    exports fr.simplon.lordofsimplon.impl.player;
    exports fr.simplon.lordofsimplon.impl;
    exports fr.simplon.lordofsimplon.api.fight;
    exports fr.simplon.lordofsimplon.api.player;
    exports fr.simplon.lordofsimplon.api.persist;
    exports fr.simplon.lordofsimplon.api.heroes;

    requires fr.simplon.www;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens fr.simplon.lordofsimplon.impl.jpa to org.hibernate.orm.core;
}