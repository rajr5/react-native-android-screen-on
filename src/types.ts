export interface WakeLockModule {
  setPartialWakeLock: () => Promise<boolean>;
  releasePartialWakeLock: () => Promise<boolean>;
  isPartialWakeLocked: () => Promise<boolean>;
  setWakeLock: () => Promise<boolean>;
  releaseWakeLock: () => Promise<boolean>;
  isWakeLocked: () => Promise<boolean>;
}
