package beta.tiredb56.api.extension.processors;

import beta.tiredb56.api.extension.processors.extensions.blatant.CPSDrop;
import beta.tiredb56.api.extension.processors.extensions.blatant.RotationProcessor;

public class BlatantProcessor {

	public BlatantProcessor INSTANCE;

	public RotationProcessor rotationProcessor;

	public CPSDrop cpsDrop;

	public BlatantProcessor() {
		INSTANCE = this;
		this.rotationProcessor = new RotationProcessor();
		this.cpsDrop = new CPSDrop();
	}

}
