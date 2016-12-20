package dd;

public interface DDebugger<T> {
	CauseEffectChain debug(Challenge<T> c);
}

