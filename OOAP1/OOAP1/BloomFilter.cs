using System.Collections;

namespace OOAP1_10
{
    public interface IBloomFilter
    {
        /// <summary>
        /// постусловие: факт добавления указанного значения сохранен
        /// </summary>
        void Add(string value);
        
        bool IsValue(string value);
    }
    
    public class BloomFilter : IBloomFilter
    {
        private int filter_len;
        private BitArray _bitArray;

        public enum OperationStatus
        {
            NIL,
            OK,
            ERR
        }

        public OperationStatus AddStatus { get; private set; } = OperationStatus.NIL;
        
        public BloomFilter(int f_len)
        {
            filter_len = f_len;
            _bitArray = new BitArray(f_len);
        }

        public void Add(string str1)
        {
            var pos1 = Hash1(str1);
            var pos2 = Hash2(str1);

            _bitArray.Set(pos1, true);
            _bitArray.Set(pos2, true);
            AddStatus = OperationStatus.OK;
        }

        public bool IsValue(string str1)
        {
            var pos1 = Hash1(str1);
            var pos2 = Hash2(str1);

            if (_bitArray.Get(pos1) && _bitArray.Get(pos2))
            {
                return true;
            }
            return false;
        }
        
        private int Hash1(string str1)
        {
            var randomValue = 17;
            decimal result = 0;
            for (int i = 0; i < str1.Length; i++)
            {
                var code = (int)(str1[i]);
                result = result * randomValue + (int)(str1[i]);
            }
            return (int)(result % filter_len);
        }
        
        private int Hash2(string str1)
        {
            var randomValue = 223;
            decimal result = 0;
            for (int i = 0; i < str1.Length; i++)
            {
                result = result * randomValue + (int)str1[i];
            }
            return (int)(result % filter_len);
        }
    }
}