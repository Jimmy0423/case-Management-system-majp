package se.majp.cms.web.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import se.majp.cms.model.Credential;
import se.majp.cms.model.Issue;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class CredentialJsonMapper implements MessageBodyReader<Credential>
{
	private Gson gson;

	public CredentialJsonMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(Issue.class, new CredentialAdapter()).create();
	}
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Credential.class);
	}

	@Override
	public Credential readFrom(Class<Credential> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException
	{
		Credential credential = gson.fromJson(new InputStreamReader(entityStream), Credential.class);
		
		return credential;
	}
	
	private static final class CredentialAdapter implements JsonDeserializer<Credential>
	{
		@Override
		public Credential deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			JsonObject jsonIssue = json.getAsJsonObject();
			
			String email = jsonIssue.get("email").getAsString();
			String password = jsonIssue.get("password").getAsString();
			
			return new Credential(email, password);
		}
		
	}

}
