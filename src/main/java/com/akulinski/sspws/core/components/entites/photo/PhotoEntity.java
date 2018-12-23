package com.akulinski.sspws.core.components.entites.photo;

import com.akulinski.sspws.core.components.entites.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "photo")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhotoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_photo")
    private Integer id;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Column(name = "title")
    private String title;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "photo")
    private PhotoDescriptionEntity description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id_album")
    private AlbumEntity albumEntity;
}
