package de.diedavids.cuba.ceuesr.entity;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import de.diedavids.jmix.softreference.cuba.entity.EntitySoftReferenceConverter;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.PropertyDatatype;

import javax.persistence.*;

@JmixEntity
@Table(name = "CEUESR_DOCUMENT")
@Entity(name = "ceuesr_Document")
public class Document extends StandardEntity {
    @Column(name = "NAME")
    protected String name;

    @JmixProperty
    @Column(name = "REFERS_TO")
    @PropertyDatatype("EntitySoftReference")
    @Convert(converter = EntitySoftReferenceConverter.class)
    protected Object refersTo;

    @PropertyDatatype("SoftReference")
    @Column(name = "REFERS_TO_JMIX")
    private Object refersToJmix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;

    public SupportsDocumentReference getRefersToJmix() {
        return (SupportsDocumentReference) refersToJmix;
    }

    public void setRefersToJmix(SupportsDocumentReference refersToJmix) {
        this.refersToJmix = refersToJmix;
    }

    public FileDescriptor getFile() {
        return file;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }

    public SupportsDocumentReference getRefersTo() {
        return (SupportsDocumentReference) refersTo;
    }

    public void setRefersTo(SupportsDocumentReference refersTo) {
        this.refersTo = refersTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}