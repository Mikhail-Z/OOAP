using System;

namespace OOAP1_9
{
    public enum OperationStatus
    {
        NIL,
        OK,
        ERR
    }
    
    public interface IPowerSet<T>
    {
        /// <summary>
        /// предусловие: в хэш-таблице остались незаполненные ячейки и указанное значение отсутствует во множестве
        /// постусловие: во множество добавлено новое уникальное указанное значение
        /// </summary>
        void Put(T value);
        
        bool Contains(T value);

        /// <summary>
        /// предусловие: указанное значение присутствует во множестве
        /// постусловие: из множества удалено указанное значение
        /// </summary>
        void Remove(T value);

        /// <summary>
        /// постусловие: создано новое множество, состоящее из пересечения между текущим множеством и указанным в аргментах множеством
        /// </summary>
        PowerSet<T> Intersection(PowerSet<T> set2);

        /// <summary>
        /// постусловие: создано новое множество, состоящее из объединения между текущим множеством и указанным в аргментах множеством
        /// </summary>
        PowerSet<T> Union(PowerSet<T> set2);

        /// <summary>
        /// постусловие: создано новое множество, состоящее из разницы между текущим множеством и указанным в аргментах множеством
        /// </summary>
        PowerSet<T> Difference(PowerSet<T> set2);
        
        bool IsSubset(PowerSet<T> set2);
    }
    
    public class PowerSet<T>: HashTable, IPowerSet<T>
    {
        public PowerSet(int maxSize) : base(maxSize)
        {}

        public OperationStatus PutStatus { get; private set; } = OperationStatus.NIL;
        public OperationStatus RemoveStatus { get; private set; } = OperationStatus.NIL;
        
        public void Put(T value)
        {
            if (Contains(value.ToString()))
            {
                PutStatus = OperationStatus.ERR;
                return;
            }
            
            base.Put(value.ToString());
            PutStatus = base.PutStatus;
        }

        public bool Contains(T value)
        {
            return base.Contains(value.ToString());
        }

        public void Remove(T value)
        {
            base.Remove(value.ToString());
            RemoveStatus = base.RemoveStatus;
        }

        public PowerSet<T> Intersection(PowerSet<T> set2)
        {
            var intersection = new PowerSet<T>(Math.Max(Size(), set2.Size()));
            foreach (var value in base.slots)
            {
                if (set2.Contains(value))
                {
                    intersection.Put(value);
                }
            }

            return intersection;
        }

        public PowerSet<T> Union(PowerSet<T> set2)
        {
            var union = new PowerSet<T>(Size() + set2.Size());
            foreach (var value in slots)
            {
                union.Put(value);
            }

            foreach (var value2 in set2.slots)
            {
                union.Put(value2);
            }

            return union;
        }

        public PowerSet<T> Difference(PowerSet<T> set2)
        {
            var difference = new PowerSet<T>(Math.Max(Size(), set2.Size()));
            foreach (var value in slots)
            {
                difference.Put(value);
            }
            foreach (var value2 in set2.slots)
            {
                difference.Remove(value2);
            }
            return difference;
        }

        public bool IsSubset(PowerSet<T> set2)
        {
            foreach (var value2 in set2.slots)
            {
                if (Contains(value2) == false)
                {
                    return false;
                }
            }
            return true;
        }
    }
    
    public class HashTable
    {
        private int size;
        private int step;
        protected string[] slots;

        private const int DEFAULT_STEP = 9;

        protected OperationStatus PutStatus { get; private set; } = OperationStatus.NIL;
        protected OperationStatus RemoveStatus { get; private set; } = OperationStatus.NIL;
        
        protected HashTable(int sz)
        {
            size = sz;
            step = DEFAULT_STEP;
            slots = new string[size];
            for (int i = 0; i < size; i++) slots[i] = null;
        }
        
        protected bool Contains(string value)
        {
            var index = SeekUsedSlot(value);
            if (index != -1)
            {
                return true;
            }

            return false;
        }

        protected void Put(string value)
        {
            var index = SeekFreeSlot(value);
            if (index == -1)
            {
                PutStatus = OperationStatus.ERR;
            }

            slots[index] = value;
            PutStatus = OperationStatus.OK;
        }

        protected void Remove(string value)
        {
            var index = SeekUsedSlot(value);
            if (index != -1)
            {
                slots[index] = null;
                RemoveStatus = OperationStatus.OK;
            }

            RemoveStatus = OperationStatus.ERR;
        }

        protected int Size()
        {
            int curSize = 0;
            for (int i = 0; i < slots[i].Length; i++)
            {
                if (slots[i] != null)
                {
                    curSize++;
                }
            }

            return curSize;
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
}