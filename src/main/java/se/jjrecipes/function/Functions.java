package se.jjrecipes.function;

import se.jjrecipes.entity.Tag;

import com.google.common.base.Function;

public class Functions {
	
	public static Function<Tag, Long> tagToIds = new Function<Tag, Long>() {
		public Long apply(Tag tag) {
			return tag.getId();
		};
	};
}
