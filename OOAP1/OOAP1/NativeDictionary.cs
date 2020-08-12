using System;

namespace OOAP1_8
{
    public enum OperationStatus
    {
        NIL,
        OK,
        ERR
    }
    
    public interface INativeDictionary<T>
    {
        bool IsKey(string key);
        
        /// <summary>
        /// постусловие: в словарь добавлен новый элемент
        /// </summary>
        void Put(string key, T value);
        
        /// <summary>
        /// предусловие: в словаре присутствует элемент с указанным ключом
        /// постусловие: из словаря удален элемент с указанным ключом
        /// </summary>
        T Remove(string key);
        
        /// <summary>
        /// предусловие: в словаре присутствует элемент с указанным ключом
        /// </summary>
        T Get(string key);
    }
    
    public class NativeDictionary<T> : INativeDictionary<T>
    {
        public int size;
        public string[] slots;
        public T[] values;
       
        private int step = 3;

        public OperationStatus RemoveStatus { get; private set; } = OperationStatus.NIL;
        public OperationStatus PutStatus { get; private set; } = OperationStatus.NIL;
        public OperationStatus GetStatus { get; private set; } = OperationStatus.NIL;

        public NativeDictionary(int sz)
        {
            size = sz;
            slots = new string[size];
            values = new T[size];
        }

        public bool IsKey(string key)
        {
            var index = SeekUsedSlot(key);
            return index != -1;
        }

        public void Put(string key, T value)
        {
            var index = SeekFreeSlot(key);
            if (index == -1)
            {
                PutStatus = OperationStatus.ERR;
                return;
            }

            slots[index] = key;
            values[index] = value;
            PutStatus = OperationStatus.OK;
        }

        public T Remove(string key)
        {
            var index = SeekUsedSlot(key);
            if (index == -1)
            {
                RemoveStatus = OperationStatus.ERR;
                return default;
            }

            var value = values[index];
            slots[index] = null;
            values[index] = default;
            RemoveStatus = OperationStatus.OK;
            return value;
        }

        public T Get(string key)
        {
            var index = SeekUsedSlot(key);
            if (index == -1)
            {
                GetStatus = OperationStatus.ERR;
                return default;
            }

            GetStatus = OperationStatus.OK;
            return values[index];
        }

        private int HashFun(string key)
        {
            int charTableCodesSum = 0;
            for (int i = 0; i < key.Length; i++)
            {
                charTableCodesSum += key[i];
            }

            return charTableCodesSum % size;
        }

        private int SeekFreeSlot(string value)
        {
            foreach (var index in GetSlotsIndexes(value))
            {
                if (slots[index] == null)
                {
                    return index;
                }
            }

            return -1;
        }

        private int SeekUsedSlot(string value)
        {
            foreach (var index in GetSlotsIndexes(value))
            {
                if (slots[index] == value)
                {
                    return index;
                }
            }

            return -1;
        }

        private System.Collections.Generic.IEnumerable<int> GetSlotsIndexes(string value)
        {
            var slot0 = HashFun(value);
            yield return slot0;
            
            if (size % step == 0)
            {
                var curStep = step;
                while (curStep > 0)
                {
                    var stepsCount = size / curStep;
                    for (int i = 1; i < stepsCount; i++)
                    {
                        int slot = (slot0 + i * curStep) % size;
                        yield return slot;
                    }
                    curStep--;
                }
            }
            else
            {
                var curIndex = slot0;
                do
                {
                    curIndex = (curIndex + step) % size;
                    if (slots[curIndex] == null)
                    {
                        yield return curIndex;
                    }
                }
                while (curIndex != slot0);
            }
        }
    }
}