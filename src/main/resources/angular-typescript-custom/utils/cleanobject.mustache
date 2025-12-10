export function cleanObject<T>(value: T): T {
  if (value === null || value === undefined || value === "") {
    return undefined as any;
  }
  if (Array.isArray(value)) {
    const cleanedArray = value
      .map((item) => cleanObject(item))
      .filter((item) => {
        if (item === undefined || item === null || item === "") return false;
        if (typeof item === "object" && Object.keys(item).length === 0)
          return false;
        return true;
      });
    if (cleanedArray.length === 0) return undefined as any;

    return cleanedArray as any;
  }
  if (typeof value === "object") {
    const obj: any = {};

    for (const key of Object.keys(value as any)) {
      const cleanedValue = cleanObject((value as any)[key]);

      if (
        cleanedValue !== undefined &&
        cleanedValue !== null &&
        cleanedValue !== "" &&
        !(typeof cleanedValue === "object" && Object.keys(cleanedValue).length === 0)
      ) {
        obj[key] = cleanedValue;
      }
    }
    if (Object.keys(obj).length === 0) return undefined as any;

    return obj;
  }
  return value;
}
