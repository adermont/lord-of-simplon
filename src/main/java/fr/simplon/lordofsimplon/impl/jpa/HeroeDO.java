package fr.simplon.lordofsimplon.impl.jpa;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "heroe", schema = "lordofsimplon")
public class HeroeDO
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int    id;
    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    public int getId()
    {
        return id;
    }

    public void setId(int pId)
    {
        id = pId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String pName)
    {
        name = pName;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object pO)
    {
        if (this == pO)
        {
            return true;
        }
        if (pO == null || getClass() != pO.getClass())
        {
            return false;
        }
        HeroeDO heroeDO = (HeroeDO) pO;
        return id == heroeDO.id && Objects.equals(name, heroeDO.name);
    }
}
