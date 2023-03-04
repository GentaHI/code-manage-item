package org.genta.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "item")
public class item extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "idItemSeq", sequenceName = "item_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "idItemSeq")
    @Column(name ="id", nullable = false)
    public Long id;

    @Column(name = "nama_barang")
    public String name;
    @Column(name = "count")
    public Long count;
    @Column(name = "harga")
    public Long price;
    @Column(name = "type")
    public String type;
    @Column(name = "deskripsi")
    public String description;
    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "update_at")
    public LocalDateTime updateAt;
}
