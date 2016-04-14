package se.jjrecipes.function;

import se.jjrecipes.data.TagData;
import se.jjrecipes.entity.Tag;

import com.google.common.base.Function;

public class Functions {
	
	public static Function<Tag, Long> tagToIds = new Function<Tag, Long>() {
		public Long apply(Tag tag) {
			return tag.getId();
		};
	};
	
	public static Function<Long, Tag> idsToTags = new Function<Long, Tag>() {
		@Override
		public Tag apply(Long id) {
			return TagData.get(Tag.class, id);
		}
	};
	
	public static Function<String, Long> stringsToLongs = new Function<String, Long>() {
		
		@Override
		public Long apply(String s) {
			return Long.valueOf(s);
		}
	};
}
