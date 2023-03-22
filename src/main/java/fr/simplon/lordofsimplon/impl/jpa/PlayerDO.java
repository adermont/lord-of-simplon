package fr.simplon.lordofsimplon.impl.jpa;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "player", schema = "lordofsimplon")
public class PlayerDO
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int     id;
    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String  name;
    @Basic
    @Column(name = "victory_count", nullable = false)
    private int     victoryCount;
    @ManyToOne
    @JoinColumn(name = "id_heroe", referencedColumnName = "id")
    private HeroeDO heroe;

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

    public int getVictoryCount()
    {
        return victoryCount;
    }

    public void setVictoryCount(int pVictoryCount)
    {
        victoryCount = pVictoryCount;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, victoryCount);
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
        PlayerDO playerDO = (PlayerDO) pO;
        return id == playerDO.id && victoryCount == playerDO.victoryCount && Objects.equals(name, playerDO.name);
    }

    public HeroeDO getHeroe()
    {
        return heroe;
    }

    public void setHeroe(HeroeDO pHeroe)
    {
        heroe = pHeroe;
    }
}
