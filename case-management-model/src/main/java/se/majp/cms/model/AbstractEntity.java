package se.majp.cms.model;

import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public class AbstractEntity
{
	@Id
	@GeneratedValue
	private Long id;
	
    @LastModifiedDate
    private Date modificationTime;
	
	@PrePersist
	public void prePersist()
	{
		modificationTime = Calendar.getInstance().getTime();
	}
	
	@PreUpdate
	public void preUpdate()
	{
		modificationTime = Calendar.getInstance().getTime();
	}

	public Long getId()
	{
		return id;
	}
	
	public Date getModificationTime()
	{
		return modificationTime;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
