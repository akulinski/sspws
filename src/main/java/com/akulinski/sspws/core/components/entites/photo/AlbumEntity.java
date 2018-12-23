package com.akulinski.sspws.core.components.entites.photo;

import com.akulinski.sspws.core.components.entites.BaseEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Table(name = "album")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AlbumEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_album")
    private Integer id;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "album_description")
    private String albumDescription;

    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "albumEntity")
    @JsonIgnore
    private Set<PhotoEntity> photoEntitySet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id_user")
    @JsonIgnore
    private UserEntity userEntity;
}
