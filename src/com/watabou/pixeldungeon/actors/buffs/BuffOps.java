package com.watabou.pixeldungeon.actors.buffs;

import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.actors.Char;

public class BuffOps {
	public static <T extends Buff> T affect(Char target, Class<T> buffClass) {
		T buff = target.getBuff(buffClass);
		if (buff != null) {
			return buff;
		} else {
			try {
				buff = buffClass.newInstance();
				buff.attachTo(target);
				return buff;
			} catch (Exception  e) {
				PixelDungeon.reportException(e);
				return null;
			}
		}
	}

	public static <T extends Buff> T affect(Char target,
			Class<T> buffClass, float duration) {
		T buff = affect(target, buffClass);
		target.getBuff(buff.getClass()).spend(duration);
		return buff;
	}

	public static <T extends Buff> T prolong(Char target,
			Class<T> buffClass, float duration) {
		T buff = affect(target, buffClass);
		buff.postpone(duration);
		return buff;
	}

	public static void detach(Buff buff) {
		if (buff != null) {
			buff.detach();
		}
	}

	public static void detach(Char target, Class<? extends Buff> cl) {
		detach(target.getBuff(cl));
	}
}