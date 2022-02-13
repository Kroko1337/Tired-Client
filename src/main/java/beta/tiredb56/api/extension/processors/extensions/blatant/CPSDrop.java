package beta.tiredb56.api.extension.processors.extensions.blatant;

import beta.tiredb56.api.util.TimerUtil;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.api.util.MathUtil;

import java.util.Random;

public class CPSDrop implements IHook {

	private TimerUtil timerUtil = new TimerUtil();

	private boolean wasCPSDrop;

	private int currCPS;

	public boolean IsCriteriaForCpsMax = false;

	public int getNeededClicks(int minCPS, int maxCPS) {
		final Random random = new Random();
		currCPS = minCPS + (random.nextInt() * (maxCPS - minCPS));

		if (timerUtil.reachedTime((long) (1000.0 / MathUtil.getRandom(minCPS, maxCPS)))) {
			wasCPSDrop = !wasCPSDrop;
			timerUtil.doReset();
		}

		final double curr = System.currentTimeMillis() * random.nextInt(220);

		double timeCovert = Math.max(currCPS, curr) / 3;
		if (wasCPSDrop) {
			currCPS = (int) timeCovert;
		} else {
			currCPS = (int) (minCPS + (random.nextInt() * (maxCPS - minCPS)) / timeCovert);
		}

		return currCPS;
	}

}
