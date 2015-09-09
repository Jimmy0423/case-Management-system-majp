package se.majp.caseManagement.web.mapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.majp.caseManagement.model.Story;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class StoryJsonMapper implements MessageBodyWriter<Story>
{
	private Gson gson;
	
	public StoryJsonMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(Story.class, new StoryAdapter()).create();
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Story.class);
	}

	@Override
	public long getSize(Story t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(Story story, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(story, Story.class, writer);
		}
	}
	private static final class StoryAdapter implements JsonSerializer<Story>

	{
		@Override
		public JsonElement serialize(Story story, Type typeOfSrc, JsonSerializationContext context)
		{
			JsonObject jsonStory = new JsonObject();
			jsonStory.add("storyId", new JsonPrimitive(story.getStoryId()));
			jsonStory.add("name", new JsonPrimitive(story.getName()));
			jsonStory.add("description", new JsonPrimitive(story.getDescription()));
			jsonStory.add("status", new JsonPrimitive(story.getStatus().toString()));
			jsonStory.add("priority", new JsonPrimitive(story.getPriority().toString()));

			return jsonStory;
		}
	}
}
