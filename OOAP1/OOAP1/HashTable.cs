using System.Collections;

namespace OOAP1
{
    public enum OperationStatus
    {
        NIL,
        OK,
        ERR
    }
    
    public interface IHashTable
    {
        bool Contains(string value);
        
        /// <summary>
        /// предусловие: хэш-таблица не заполнена полностью
        /// постусловие: в хэш-таблицу добавлен новый элемент
        /// </summary>
        void Put(string value);
        
        /// <summary>
        /// предусловие: в хэш-таблице есть элемент, результат хэш-функции от которого совпадает с результатом хэш-функции от указанного значения 
        /// постусловие: из хэш-таблицы удален элемент, результат хэш-функции от которого совпадает с результатом хэш-функции от указанного значения
        /// </summary>
        void Remove(string value);
    }
    
    public class HashTable : IHashTable
    {
        private int size;
        private int step;
        private string[] slots;

        private const int DEFAULT_STEP = 9;

        public OperationStatus PutStatus { get; private set; } = OperationStatus.NIL;
        public OperationStatus RemoveStatus { get; private set; } = OperationStatus.NIL;
        
        public HashTable(int sz)
        {
            size = sz;
            step = DEFAULT_STEP;
            slots = new string[size];
            for (int i = 0; i < size; i++) slots[i] = null;
        }
        
        public bool Contains(string value)
        {
            var index = SeekUsedSlot(value);
            if (index != -1)
            {
                return true;
            }

            return false;
        }

        public void Put(string value)
        {
            var index = SeekFreeSlot(value);
            if (index == -1)
            {
                PutStatus = OperationStatus.ERR;
            }

            slots[index] = value;
            PutStatus = OperationStatus.OK;
        }

        public void Remove(string value)
        {
            var index = SeekUsedSlot(value);
            if (index != -1)
            {
                slots[index] = null;
                RemoveStatus = OperationStatus.OK;
            }

            RemoveStatus = OperationStatus.ERR;
        }
        
        private int HashFun(string value)
        {
            int charTableCodesSum = 0;
            for (int i = 0; i < value.Length; i++)
            {
                charTableCodesSum += value[i];
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

    public interface IHashTableFactory
    {
        /// <summary>
        /// постусловие: создает новый экземляр хэш-таблицы
        /// </summary>
        IHashTable Create(int size);
    }
    
    public class HashTableFactory : IHashTableFactory
    {
        public OperationStatus CreateStatus { get; private set; } = OperationStatus.NIL;
        
        public IHashTable Create(int size)
        {
            if (size <= 0)
            {
                CreateStatus = OperationStatus.ERR;
                return default;
            }
            else
            {
                CreateStatus = OperationStatus.OK;
                return new HashTable(size);
            }
        }
    }
}