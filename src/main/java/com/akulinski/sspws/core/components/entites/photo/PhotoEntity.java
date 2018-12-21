package com.akulinski.sspws.core.components.entites.photo;

import com.akulinski.sspws.core.components.entites.BaseEntity;
import com.akulinski.sspws.core.components.entites.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "link",columnDefinition = "TEXT")
    private String link;

    @Column(name = "album")
    private String album;

    @Column(name = "title")
    private String title;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "photo")
    private PhotoDescriptionEntity description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id_user")
    private UserEntity userEntity;
}
