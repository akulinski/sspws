package com.akulinski.sspws.core.components.entites.photo;

import com.akulinski.sspws.core.components.entites.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "photo_description")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhotoDescriptionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_photo_description")
    private Integer id;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_photo", nullable = false)
    private PhotoEntity photo;

}
